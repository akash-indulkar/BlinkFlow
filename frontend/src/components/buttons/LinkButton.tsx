import { ReactNode } from "react"

export const LinkButton = ({ children, onClick }: { children: ReactNode, onClick: () => void }) => {
    return (
        <div
            className="flex items-center justify-center 
               bg-white hover:bg-slate-100 
               border text-sm font-light 
               px-2 sm:px-6 md:px-8 py-2 
               rounded-full cursor-pointer 
               transition duration-150 ease-in-out"
            onClick={onClick}
        >
            {children}
        </div>
    );

}