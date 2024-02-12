const path = require("path");

module.exports = {
  module: {
    rules: [
      {
        test: /\.ts$/,
        use: ["ts-loader"],
        exclude: /node_modules/,
      },
      {
        test: /mapbox-gl.+\.js$/,
        use: ["transform/cacheable?brfs"],
      },
    ],
  },
  mode: "production",
  entry: "./src/index.ts",
  output: {
    path: path.join(__dirname, "dist"),
    publicPath: "/",
    filename: "final.js",
  },
  target: "node",
  resolve: {
    extensions: [".ts", ".js", ".json"],
  },
};
