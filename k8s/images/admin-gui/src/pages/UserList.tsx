import { useEffect, useState } from "react";
import type { User } from "../types/User";
import axios from "axios";

function UserList() {
    const [users, setUsers] = useState<Array<User>>([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        axios.get("http://localhost:8080/api/v1/users")
            .then(response => {
                setUsers(response.data);
            })
            .catch(error => {
                console.error("Error fetching users:", error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    const filteredUsers = users.filter(user =>
        user.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const getInitials = (name: string) => {
        return name
            .split(' ')
            .map(part => part[0])
            .join('')
            .slice(0, 2);
    };

    return (
        <div className="app-container">
            {/* Header */}
            <header className="header">
                <h1 className="header__title">Admin Dashboard</h1>
                <p className="header__subtitle">Manage your users and access controls</p>
            </header>

            {/* Stats Bar */}
            <div className="stats-bar">
                <div className="stat-card">
                    <div className="stat-value">{users.length}</div>
                    <div className="stat-label">Total Users</div>
                </div>
                <div className="stat-card">
                    <div className="stat-value">{users.filter(u => u.id).length}</div>
                    <div className="stat-label">Active Users</div>
                </div>
                <div className="stat-card">
                    <div className="stat-value">∞</div>
                    <div className="stat-label">Possibilities</div>
                </div>
            </div>

            {/* Search */}
            <div className="search-container">
                <div className="search-wrapper">
                    <svg className="search-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
                    </svg>
                    <input
                        type="text"
                        className="search-input"
                        placeholder="Search users..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
            </div>

            {/* User List Card */}
            <div className="card">
                <div className="card__header">
                    <h2 className="card__title">
                        <svg className="card__title-icon" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" />
                        </svg>
                        Users
                    </h2>
                    <span className="user-badge">{filteredUsers.length} results</span>
                </div>
                <div className="card__body">
                    {loading ? (
                        <div className="loading">
                            <div className="loading__spinner"></div>
                            <span className="loading__text">Loading users...</span>
                        </div>
                    ) : filteredUsers.length === 0 ? (
                        <div className="empty-state">
                            <svg className="empty-state__icon" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm-1-4h2v2h-2zm0-10h2v8h-2z" />
                            </svg>
                            <h3 className="empty-state__title">No users found</h3>
                            <p className="empty-state__description">
                                {searchTerm ? "Try adjusting your search term" : "No users have been added yet"}
                            </p>
                        </div>
                    ) : (
                        <ul className="user-list">
                            {filteredUsers.map((user, index) => (
                                <li
                                    key={user.id}
                                    className="user-item"
                                    style={{ animationDelay: `${index * 0.05}s` }}
                                >
                                    <div className="user-avatar">
                                        {getInitials(user.name)}
                                    </div>
                                    <div className="user-info">
                                        <div className="user-name">{user.name}</div>
                                        <div className="user-email">User ID: {user.id}</div>
                                    </div>
                                    <span className="user-badge">Active</span>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
        </div>
    );
}

export default UserList;    