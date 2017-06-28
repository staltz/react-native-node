#!/usr/bin/env node
const path = require("path");

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

console.log(
  "We will bundle and insert the directory\n" +
    path.resolve(process.cwd(), argv.path)
);
