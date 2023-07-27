import "dotenv/config";
interface Config {
    URL: string;
}

let config: Config;



try {
    config = require("../config.json");
    console.log("Using config.json");
} catch (error) {
    console.log("Using environment variables");
    config = {
        URL: process.env.URL || "localhost:3001",
    };
}

export { config };