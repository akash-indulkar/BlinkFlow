# USE BASE node:18-alpine to build the application
FROM node:18-alpine AS builder
# Set the working directory
WORKDIR /app
# Copy package.json and package-lock.json
COPY package*.json ./
# Install dependencies
RUN npm install
# Copy the rest of the application code
COPY . .
# Build the application
RUN npm run build
# Use nginx to serve the built application
FROM nginx:stable-alpine
# Copy the built application from the builder stage
COPY --from=builder /app/dist /usr/share/nginx/html
# Copy the nginx configuration file
COPY nginx.conf /etc/nginx/conf.d/default.conf
# Expose port 80 for the application
EXPOSE 80
# Start nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]