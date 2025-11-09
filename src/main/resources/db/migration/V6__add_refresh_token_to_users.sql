-- Add refresh token columns to users table for JWT authentication
ALTER TABLE users
ADD COLUMN refresh_token VARCHAR(500),
ADD COLUMN refresh_token_expiry_time TIMESTAMP;
