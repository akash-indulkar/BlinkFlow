import { ReactNode, useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";
import { Loader } from "../components/Loader";

export const PrivateRoute = ({ children } : {children : ReactNode}) => {
  const { isLoading, isLoggedIn } = useContext(AuthContext);
  if(isLoading){
    return <Loader/>
  }
  return isLoggedIn ? children : <Navigate to="/login/redirect" />;
};
