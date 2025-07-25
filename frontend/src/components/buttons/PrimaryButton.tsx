import { ReactNode } from "react"

export const PrimaryButton = ({ children, onClick, size = "small", isLoading, minWidth }: {
    children: ReactNode,
    onClick: () => void,
    size?: "big" | "small" | "medium",
    isLoading: boolean,
    minWidth: string
}) => {
    if (isLoading) {
        return (
            <div
                onClick={onClick}
                className={`flex justify-center items-center 
                  ${size === "small" ? "text-sm" : size === "medium" ? "text-base" : "text-base md:text-xl"} 
                  ${minWidth} 
                  ${size === "big" ? "px-6 py-3 md:px-10 md:py-4" : "px-7 py-2"} 
                  font-[450] cursor-pointer shadow-xl 
                  bg-amber-700 text-white rounded-full`}>
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth={1.5}
                    stroke="currentColor"
                    className={`animate-spin 
                    ${size === "small" ? "size-5" : size === "medium" ? "size-6" : "size-10"}`}>
                    <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0 
             3.181 3.183a8.25 8.25 0 0 0 13.803-3.7M4.031 9.865a8.25 8.25 0 0 1 
             13.803-3.7l3.181 3.182m0-4.991v4.99"/>
                </svg>
            </div>
        );
    }

    return (
        <div
            onClick={onClick}
            className={`flex justify-center items-center 
                ${size === "small" ? "text-sm" : size === "medium" ? "text-base" : "text-base md:text-xl"} 
                ${minWidth} 
                ${size === "big" ? "px-6 py-3 md:px-10 md:py-4" : "px-7 py-2"} 
                font-[450] cursor-pointer shadow-xl 
                bg-amber-700 text-white rounded-full`}>
            {children}
        </div>
    );

}