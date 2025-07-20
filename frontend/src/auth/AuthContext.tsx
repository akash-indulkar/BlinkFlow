  import { createContext, useEffect, useState, ReactNode } from "react";

  export const AuthContext = createContext({
    isLoggedIn: false,
    login: (token: string) => {},
    logout: () => {},
  });

  export const AuthProvider = ({children} : {children : ReactNode}) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
      const token = localStorage.getItem("token");
      setIsLoggedIn(!!token);
    }, []);

    const login = (token: string) => {
      localStorage.setItem("token", token);
      setIsLoggedIn(true);
    };

    const logout = () => {
      localStorage.removeItem("token");
      setIsLoggedIn(false);
    };

    return (
      <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
        {children}
      </AuthContext.Provider>
    );
  };
