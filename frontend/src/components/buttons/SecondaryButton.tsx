import { ReactNode } from "react"

export const SecondaryButton = ({ children, onClick, size = "small" }: {
    children: ReactNode,
    onClick: () => void,
    size?: "big" | "small"
}) => {
    return (
        <div
            onClick={onClick}
            className={`
                min-w-[250px]
                text-center
                cursor-pointer 
                rounded-full 
                border border-black 
                text-black 
                hover:shadow-md 
                ${size === "small" ? "text-sm px-8 pt-2" : "text-base md:text-xl px-6 py-3 md:px-10 md:py-4"}`}>
            {children}
        </div>
    );

}