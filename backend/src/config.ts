import "dotenv/config";
interface Config {
  MYSQL_DATABASE: string;
  MYSQL_USER: string;
  MYSQL_PASSWORD: string;
  MYSQL_HOST: string;
  BACKEND_SECRET_KEY: string;
}

let config: Config;
console.log("Using environment variables");

let missingEnvVars = [];

if (!process.env.MYSQL_DATABASE) missingEnvVars.push("MYSQL_DATABASE");
if (!process.env.MYSQL_PASSWORD) missingEnvVars.push("MYSQL_PASSWORD");
if (!process.env.MYSQL_USER) missingEnvVars.push("MYSQL_USER");
if (!process.env.MYSQL_HOST) missingEnvVars.push("MYSQL_HOST");
if (!process.env.BACKEND_SECRET_KEY) missingEnvVars.push("BACKEND_SECRET_KEY");

if (missingEnvVars.length > 0) {
  console.error(`Missing environment variables: ${missingEnvVars.join(", ")}`);
  process.exit(1);
}

config = {
  MYSQL_DATABASE: process.env.MYSQL_DATABASE!,
  MYSQL_PASSWORD: process.env.MYSQL_PASSWORD!,
  MYSQL_USER: process.env.MYSQL_USER!,
  MYSQL_HOST: process.env.MYSQL_HOST!,
  BACKEND_SECRET_KEY: process.env.BACKEND_SECRET_KEY!,
};
export { config };
