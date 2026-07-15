import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate, Outlet, useLocation } from 'react-router-dom';

const PrivateRoute = ({ publicPage = false, adminOnly = false }) => {

    const { user } = useSelector((state) => state.auth);

    const location = useLocation();

    const isAdmin = user?.roles?.includes("ROLE_ADMIN");
    const isSeller = user?.roles?.includes("ROLE_SELLER");

    // Login and Register pages
    if (publicPage) {
        return user ? <Navigate to="/" replace /> : <Outlet />;
    }

    // User is not logged in
    if (!user) {
        return <Navigate to="/login" replace />;
    }

    // Admin routes
    if (adminOnly) {

        // Seller can access only these pages
        if (isSeller && !isAdmin) {

            const sellerAllowedPaths = [
                "/admin/orders",
                "/admin/products"
            ];

            const sellerAllowed = sellerAllowedPaths.some(path =>
                location.pathname.startsWith(path)
            );

            if (!sellerAllowed) {
                return <Navigate to="/" replace />;
            }
        }

        // Only admin or seller can access admin section
        if (!isAdmin && !isSeller) {
            return <Navigate to="/" replace />;
        }
    }

    // Logged-in users can access checkout
    return <Outlet />;
};

export default PrivateRoute;