import { useNavigate } from "react-router-dom";
import { Feature } from "./Feature"
import { PrimaryButton } from "../buttons/PrimaryButton"
import { SecondaryButton } from "../buttons/SecondaryButton"

export const Hero = () => {
    const router = useNavigate();
    return <div>
        <div className="flex justify-center">
            <div className="text-5xl font-bold font-semibold text-center pt-8 max-w-xl pb-8">
                Automate as fast as you can type !  
            </div>
        </div>
        <div className="flex justify-center pt-2 pb-8">
            <div className="text-xl font-bold font-normal text-center pt-8 max-w-2xl">
                AI gives you automation superpowers, and BlinkFlow puts them to work. Pairing AI and BlinkFlow helps you turn ideas into workflows and bots that work for you.
            </div>
        </div>

        <div className="flex justify-center pt-4 pb-8">
            <div className="flex">
                <PrimaryButton onClick={() => {
                    router("/signup")
                }} size="big"> Get Started for free ! </PrimaryButton>
                <div className="pl-4 min-w-44">
                    <SecondaryButton  onClick={() => {
                        router("/login")
                    }} size="big">Have an Account? Login</SecondaryButton>
                </div>
            </div>
        </div>

        <div className="flex justify-center pt-4 pb-8">
            <Feature title={"Free Forever"} subtitle={"for core features"} />
            <Feature title={"More apps"} subtitle={"than any other platforms"} />
            <Feature title={"Cutting Edge"} subtitle={"AI Features"} />
        </div>
    </div>
}