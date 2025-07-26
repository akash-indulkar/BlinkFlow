import { useEffect } from "react";
import { matchPath, useLocation } from "react-router-dom";

export const useDynamicViewport = () => {
    var location = useLocation();

    useEffect(() => {
        const viewport = document.querySelector('meta[name="viewport"]');
        if (!viewport) return;

        const isDesktopViewRoute = (location.pathname === "/dashboard" || location.pathname === "/flow/create" || matchPath("/flow/edit/:flowID", location.pathname));
        const desiredContent = isDesktopViewRoute ? "width=1024" : "width=device-width, initial-scale=1";
        
        viewport.setAttribute("content", desiredContent);
    }, [location.pathname]);

};
