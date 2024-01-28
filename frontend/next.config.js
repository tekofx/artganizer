/** @type {import('next').NextConfig} */
module.exports = {
  reactStrictMode: true,
  output: "standalone",
  env: {
    API_URL: process.env.API_URL,
  },
};
