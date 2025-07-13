import { Hero } from "../components/landing/Hero"
import { HeroVideo } from "../components/landing/HeroVideo"

export const Landing = () => {
    return <div className="pb-48 main-content">
        <Hero />
        <div className="pt-7">
            <HeroVideo />
        </div>
    </div>
}