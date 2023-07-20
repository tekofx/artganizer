import "dotenv/config";
interface Config {
    JWT_SECRET: string;
}

let config: Config;



try {
    config = require("../config.json");
    console.log("Using config.json");
} catch (error) {
    console.log("Using environment variables");
    config = {
        JWT_SECRET: process.env.JWT_SECRET || "",
    };
}

export { config };