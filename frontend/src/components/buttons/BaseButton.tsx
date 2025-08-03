import { ReactNode } from "react"

export const BaseButton= ({children, isInactive } : {
    children: ReactNode,
    isInactive: boolean
}) => {
    return <button  type="submit" className={`p-2 w-full shadow-xl ${isInactive ? "bg-slate-300 cursor-default" : "bg-amber-700"} text-white rounded text-center`}>{children}</button>
}