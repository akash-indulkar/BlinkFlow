import { useNavigate } from "react-router-dom"
import { LinkButton } from "../components/buttons/LinkButton"

export const NotFound = () => {
const router = useNavigate();
    return <div className="pt-14 flex-col justify-center">
        <h1 className="flex justify-center mt-2 md:mt-0 text-6xl sm:text-8xl md:text-9xl font-extrabold tracking-wide text-[#396bc4]">404</h1>
        <p className="flex justify-center text-2xl sm:text-3xl md:text-4xl font-bold text-[#396bc4]">Page not found!</p>
        <p className="flex justify-center text-base sm:text-lg md:text-xl text-gray-700">Page you are searching for does not exists.</p>
        <div className="flex justify-center items-center">
            <div className="mt-6 w-30 h-12 text-xl">
                <LinkButton onClick={() => { router("/") }}>Home</LinkButton>
            </div>
        </div>
        <div className="flex justify-center items-center">
            <div className="w-[500px] ">
                <img className="flex justify-center" src="https://res.cloudinary.com/dadualj4l/image/upload/v1752390714/404_ovmtg6.jpg"></img>
            </div>
        </div>
    </div>
}