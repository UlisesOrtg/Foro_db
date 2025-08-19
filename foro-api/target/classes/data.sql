INSERT INTO usuarios (id, nombre, email, password, role) VALUES
    (1, 'Demo', 'demo@foro.com', '$2a$10$u9kBv4Idq7QnH4tJ9dJb8OxQy9XgM4qg8cM2QqCqN1ZfG6C6kXqGa', 'USER');
-- password (BCrypt): demo123

-- Admin demo user (email: admin@foro.com, pass: admin123)
INSERT INTO usuarios (id, nombre, email, password, role) VALUES
    (2, 'Admin', 'admin@foro.com', '$2a$10$h0rO2aZfV4Qqk2nHh1c8T.2kH1Jr7G5k9h3o9QnJ8B7V1rZq8w1r2', 'ADMIN');
