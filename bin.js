#!/usr/bin/env node
const path = require("path");
const tar = require("tar");
const fs = require("fs");
const mkdirp = require("mkdirp");
const cheerio = require("cheerio");

const BUNDLE_TAR = "rnnodebundle.tgz";
const BUNDLE_NO_EXT = "rnnodebundle";
const SERVICE_CLASSNAME = "com.staltz.reactnativenode.RNNodeService";

function assertCanInsert(workingDir, sourcePath) {
  const androidProjectPath = path.resolve(workingDir, "./android/app");
  if (!fs.existsSync(androidProjectPath)) {
    throw new Error(
      "Android project is missing, expected in\n" +
        androidProjectPath +
        "\n" +
        "when running react-native-node insert"
    );
  }
}

function bundle(originalWorkingDir, sourcePath) {
  const absoluteSourcePath = path.resolve(originalWorkingDir, sourcePath);
  console.log("Bundling the directory " + absoluteSourcePath + " ...");
  process.chdir(absoluteSourcePath);
  const bundleFileList = fs.readdirSync(absoluteSourcePath);
  const targetAbsolutePath = path.resolve(originalWorkingDir, BUNDLE_TAR);
  tar.create(
    { gzip: false, file: targetAbsolutePath, sync: true, portable: true },
    bundleFileList
  );
  process.chdir(originalWorkingDir);
  console.log("\tdone.");
}

function insertOnly(workingDir) {
  console.log("Inserting bundle into mobile app project ...");
  const androidRawResDir = "./android/app/src/main/res/raw";
  console.log("\tInto Android project, under " + androidRawResDir + " ...");
  mkdirp.sync(androidRawResDir);
  const before = path.resolve(workingDir, BUNDLE_TAR);
  const after = path.resolve(workingDir, androidRawResDir, BUNDLE_NO_EXT);
  fs.renameSync(before, after);
  console.log("\t\tdone");
}

function updateManifest(workingDir) {
  console.log("Updating AndroidManifest.xml in mobile app project ...");
  const absoluteManifestPath = path.resolve(
    workingDir,
    "./android/app/src/main/AndroidManifest.xml"
  );
  const xmlBefore = fs.readFileSync(absoluteManifestPath, "utf-8");
  const $ = cheerio.load(xmlBefore, { xmlMode: true });
  const serviceElems = $("manifest application service").filter(
    (i, elem) => elem.attribs["android:name"] === SERVICE_CLASSNAME
  );
  if (serviceElems.length === 0) {
    $("manifest application").append(
      `<service android:name="${SERVICE_CLASSNAME}"/>`
    );
  }
  fs.writeFileSync(absoluteManifestPath, $.xml(), "utf-8");
  console.log("\tdone");
}

function insert(path) {
  const workingDir = process.cwd();
  assertCanInsert(workingDir, path);
  bundle(workingDir, path);
  insertOnly(workingDir);
  updateManifest(workingDir);
}

const argv = require("yargs")
  .usage("Usage: $0 <command> [options]")
  .example("$0 insert ./background")
  .command({
    command: "insert <path>",
    aliases: ["i"],
    desc:
      "Bundle and insert a background Node.js project from the <path> " +
      "directory into the mobile app project"
  })
  .demandCommand(1, "ERROR: You need to run a command, e.g. insert")
  .help().argv;

if (argv._[0] === "insert") {
  insert(argv.path);
}
