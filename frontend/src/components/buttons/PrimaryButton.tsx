import { ReactNode } from "react"

export const PrimaryButton = ({ children, onClick, size = "small" }: {
    children: ReactNode,
    onClick: () => void,
    size?: "big" | "small"
}) => {
    return <div onClick={onClick} className={`${size === "small" ? "text-sm" : "text-xl"} px-8 py-2 cursor-pointer shadow-xl bg-amber-700 text-white rounded-full text-center flex justify-center flex-col`}>
        {children}
    </div>
}