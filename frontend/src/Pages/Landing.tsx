import { Hero } from "../components/Hero"
import { HeroVideo } from "../components/HeroVideo"

export const Landing = () => {
    return <div className="pb-48">
        <Hero />
        <div className="pt-8">
            <HeroVideo />
        </div>
    </div>
}