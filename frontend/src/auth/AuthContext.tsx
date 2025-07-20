  import { createContext, useEffect, useState, ReactNode } from "react";

  export const AuthContext = createContext({
    isLoggedIn: false,
    login: (token: string) => {},
    logout: () => {},
    isLoading: true
  });

  export const AuthProvider = ({children} : {children : ReactNode}) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
      const token = localStorage.getItem("token");
      setIsLoggedIn(!!token);
      setIsLoading(false)
    }, []);

    const login = (token: string) => {
      localStorage.setItem("token", token);
      setIsLoggedIn(true);
      setIsLoading(false)
    };

    const logout = () => {
      localStorage.removeItem("token");
      setIsLoggedIn(false);
    };

    return (
      <AuthContext.Provider value={{ isLoggedIn, login, logout, isLoading }}>
        {children}
      </AuthContext.Provider>
    );
  };
