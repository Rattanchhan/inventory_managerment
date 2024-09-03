-- Permissions for Admin Role
INSERT INTO permissions (created_at, updated_at, code, name, module) VALUES
('2024-09-01 13:00:00', '2024-09-01 13:00:00', 'MANAGE_USERS', 'Manage Users', 'User Management'),
('2024-09-01 13:05:00', '2024-09-01 13:05:00', 'MANAGE_ROLES', 'Manage Roles', 'Role Management'),
('2024-09-01 13:10:00', '2024-09-01 13:10:00', 'MANAGE_SETTINGS', 'Manage Settings', 'System Settings'),
('2024-09-01 13:15:00', '2024-09-01 13:15:00', 'VIEW_AUDIT_LOGS', 'View Audit Logs', 'Security'),
('2024-09-01 13:20:00', '2024-09-01 13:20:00', 'FULL_ACCESS', 'Full System Access', 'All Modules');

-- Permissions for User Role
INSERT INTO permissions (created_at, updated_at, code, name, module) VALUES
('2024-09-01 13:25:00', '2024-09-01 13:25:00', 'VIEW_PROFILE', 'View Profile', 'User Management'),
('2024-09-01 13:30:00', '2024-09-01 13:30:00', 'EDIT_PROFILE', 'Edit Profile', 'User Management'),
('2024-09-01 13:35:00', '2024-09-01 13:35:00', 'VIEW_DASHBOARD', 'View Dashboard', 'Dashboard'),
('2024-09-01 13:40:00', '2024-09-01 13:40:00', 'ACCESS_FORUM', 'Access Forum', 'Community');

-- Permissions for Support Role
INSERT INTO permissions (created_at, updated_at, code, name, module) VALUES
('2024-09-01 13:45:00', '2024-09-01 13:45:00', 'VIEW_TICKETS', 'View Support Tickets', 'Support'),
('2024-09-01 13:50:00', '2024-09-01 13:50:00', 'RESPOND_TICKETS', 'Respond to Tickets', 'Support'),
('2024-09-01 13:55:00', '2024-09-01 13:55:00', 'MANAGE_TICKETS', 'Manage Support Tickets', 'Support');

-- Permissions for Guest Role
INSERT INTO permissions (created_at, updated_at, code, name, module) VALUES
('2024-09-01 14:00:00', '2024-09-01 14:00:00', 'VIEW_PUBLIC_PAGES', 'View Public Pages', 'Public Access'),
('2024-09-01 14:05:00', '2024-09-01 14:05:00', 'ACCESS_BLOG', 'Access Blog', 'Public Access');

-- Permissions for Mod Role
INSERT INTO permissions (created_at, updated_at, code, name, module) VALUES
('2024-09-01 14:10:00', '2024-09-01 14:10:00', 'MODERATE_COMMENTS', 'Moderate Comments', 'Community'),
('2024-09-01 14:15:00', '2024-09-01 14:15:00', 'BAN_USERS', 'Ban Users', 'Community'),
('2024-09-01 14:20:00', '2024-09-01 14:20:00', 'EDIT_POSTS', 'Edit User Posts', 'Community');
