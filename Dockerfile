# Use an official Node.js runtime as the parent image
FROM node:14

# Set the working directory in the container to /app
WORKDIR /app

# Copy the package.json files from your local host to the container
COPY backend/package.json ./backend/
COPY frontend/package.json ./frontend/

# Install dependencies for both backend and frontend
RUN cd backend && yarn install --network-timeout 100000
RUN cd frontend && yarn install --network-timeout 100000

# Copy the rest of your app's source code from your host to your image filesystem.
COPY . .

# Set environment variables
ENV NODE_ENV production
 # Build the frontend using Next.js
RUN cd frontend && yarn build
# Expose port 3000 for the frontend and port 8000 for the backend
EXPOSE 3000 8000

# Start both the backend and frontend
CMD cd backend && yarn start & cd frontend && yarn start
