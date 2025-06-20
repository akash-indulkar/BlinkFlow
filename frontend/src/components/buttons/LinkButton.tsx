import { ReactNode } from "react"

export const LinkButton = ({ children, onClick }: { children: ReactNode, onClick: () => void }) => {
    return <div className="flex justify-center px-8 py-2 cursor-pointer hover:bg-slate-100 font-light text-sm rounded-full" onClick={onClick}>
        {children}
    </div>
}