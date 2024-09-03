-- Assuming the role IDs and permission IDs correspond to the following:
-- Admin: 1, User: 2, Support: 3, Guest: 4, Mod: 5

-- Permissions for Admin Role
INSERT INTO role_permissions (created_at, updated_at, role_id, permission_id) VALUES
('2024-09-01 13:00:00', '2024-09-01 13:00:00', 1, 1), -- Admin: Manage Users
('2024-09-01 13:05:00', '2024-09-01 13:05:00', 1, 2), -- Admin: Manage Roles
('2024-09-01 13:10:00', '2024-09-01 13:10:00', 1, 3), -- Admin: Manage Settings
('2024-09-01 13:15:00', '2024-09-01 13:15:00', 1, 4); -- Admin: View Audit Logs

-- Permissions for User Role
INSERT INTO role_permissions (created_at, updated_at, role_id, permission_id) VALUES
('2024-09-01 13:25:00', '2024-09-01 13:25:00', 2, 5), -- User: View Profile
('2024-09-01 13:30:00', '2024-09-01 13:30:00', 2, 6), -- User: Edit Profile
('2024-09-01 13:35:00', '2024-09-01 13:35:00', 2, 7), -- User: View Dashboard
('2024-09-01 13:40:00', '2024-09-01 13:40:00', 2, 8); -- User: Access Forum

-- Permissions for Support Role
INSERT INTO role_permissions (created_at, updated_at, role_id, permission_id) VALUES
('2024-09-01 13:45:00', '2024-09-01 13:45:00', 3, 9), -- Support: View Tickets
('2024-09-01 13:50:00', '2024-09-01 13:50:00', 3, 10), -- Support: Respond to Tickets
('2024-09-01 13:55:00', '2024-09-01 13:55:00', 3, 11); -- Support: Manage Tickets

-- Permissions for Guest Role
INSERT INTO role_permissions (created_at, updated_at, role_id, permission_id) VALUES
('2024-09-01 14:00:00', '2024-09-01 14:00:00', 4, 12), -- Guest: View Public Pages
('2024-09-01 14:05:00', '2024-09-01 14:05:00', 4, 13); -- Guest: Access Blog

-- Permissions for Mod Role
INSERT INTO role_permissions (created_at, updated_at, role_id, permission_id) VALUES
('2024-09-01 14:10:00', '2024-09-01 14:10:00', 5, 14), -- Mod: Moderate Comments
('2024-09-01 14:15:00', '2024-09-01 14:15:00', 5, 15), -- Mod: Ban Users
('2024-09-01 14:20:00', '2024-09-01 14:20:00', 5, 16); -- Mod: Edit User Posts
