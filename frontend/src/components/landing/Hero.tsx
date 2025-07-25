import { useNavigate } from "react-router-dom";
import { Feature } from "./Feature"
import { PrimaryButton } from "../buttons/PrimaryButton"
import { SecondaryButton } from "../buttons/SecondaryButton"
import { useEffect, useState } from "react";
import axios from "axios";
import { Loader } from "../Loader";
import { HeroVideo } from "./HeroVideo";
import { LogoCarousel } from "../LogoCarousel";

export const Hero = () => {
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const [isLoading, setIsLoading] = useState<boolean>(true)
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)
    const router = useNavigate();
    useEffect(() => {
        try {
            axios.get(`${backendURL}/user/me`, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token"),
                    "Content-type": "application/json"
                }
            })
                .then(res => {
                    setIsLoggedIn(true)
                    setIsLoading(false)
                })
                .catch(e => {
                    console.log(e)
                    setIsLoading(false)
                })
        } catch (error) {
            console.log(error)
        }
    })
    return <div>
        {isLoading && <Loader />}
        <div className="flex justify-center px-4">
            <div className="text-3xl sm:text-5xl md:text-6xl font-gothic pb-2 md:pb-6 text-center max-w-3xl leading-snug">
                Automate as fast as you can type !
            </div>
        </div>
        <div className="flex justify-center px-4">
            <div className="text-base sm:text-lg md:text-xl font-opensans font-normal text-center max-w-3xl text-gray-700">
                AI gives you automation superpowers, and BlinkFlow puts them to work. Pairing AI and BlinkFlow helps you turn ideas into workflows and bots that work for you.
            </div>
        </div>
        <HeroVideo />
        <div className="flex justify-center mt-[20px] md:mt-[480px] pb-8 px-4">
            {isLoggedIn ?(<div className="flex flex-col sm:flex-row gap-4">
                <PrimaryButton minWidth="min-w-[250px]" isLoading={false} onClick={() => {
                    setIsLoading(true)
                    router("/flow/create")
                    setIsLoading(false)
                }} size="big"> Let's create a Flow! </PrimaryButton>
           
                <SecondaryButton onClick={() => {
                        setIsLoading(true)
                        router("/dashboard")
                        setIsLoading(false)
                    }} size="big">Go to DashBoard</SecondaryButton>
                
            </div>
            ) : (
            <div className="flex flex-col sm:flex-row gap-4">
                <PrimaryButton minWidth="min-w-[250px]" isLoading={false} onClick={() => {
                    setIsLoading(true)
                    router("/signup")
                    setIsLoading(false)
                }} size="big"> Let's Get Started for free ! </PrimaryButton>
                    <SecondaryButton onClick={() => {
                        setIsLoading(true)
                        router("/login")
                        setIsLoading(false)
                    }} size="big">Have an Account? Login</SecondaryButton>
                
            </div> )}
        </div>
        <div className="flex flex-col md:flex-row gap-4 md:gap-8 justify-center items-center pb-5 pt-1 px-4">
            <Feature title={"Free Forever"} subtitle={"for core features"} />
            <Feature title={"More apps"} subtitle={"than any other platforms"} />
            <Feature title={"Cutting Edge"} subtitle={"AI Features"} />
        </div>
        <span className="font-opensans flex pb-2 justify-center text-center  text-base sm:text-xl text-slate-500">Integrated with</span>
        <LogoCarousel />
    </div>
}