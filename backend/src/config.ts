import "dotenv/config";
interface Config {
  URL: string;
  MYSQL_DATABASE: string;
  MYSQL_USER: string;
  MYSQL_PASSWORD: string;
  MYSQL_HOST: string;
}

let config: Config;

try {
  config = require("../config.json");
  console.log("Using config.json");
} catch (error) {
  console.log("Using environment variables");
  config = {
    URL: process.env.URL || "localhost:3001",
    MYSQL_DATABASE: process.env.MYSQL_DATABASE || "db",
    MYSQL_PASSWORD: process.env.MYSQL_PASSWORD || "Contraseña",
    MYSQL_USER: process.env.MYSQL_USER || "tekofx",
    MYSQL_HOST: process.env.MYSQL_HOST || "localhost"
  };
}

export { config };
