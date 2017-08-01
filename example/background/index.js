const express = require("express");

const app = express();

app.get("/", (req, res) => {
  res.json({ msg: "This is a response from the server running in the phone!" });
});

app.listen(5000);
