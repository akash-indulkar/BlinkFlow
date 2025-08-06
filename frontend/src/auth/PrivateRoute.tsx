import { ReactNode } from "react";
import { Navigate } from "react-router-dom";

export const PrivateRoute = ({ children } : {children : ReactNode}) => {
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true" || false;

  return isLoggedIn ? children : <Navigate to="/login/redirect" />;
};
