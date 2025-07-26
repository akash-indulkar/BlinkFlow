import { useEffect, useState } from "react"

export const DeviceRecommendationToast = () => {
    const [isMobile, setIsMobile] = useState<boolean>(false)
    const [showModal, setShowModal] = useState<boolean>(true)
    useEffect(() => {
        const handleResize = () => {
            setIsMobile(window.window.screen.width < 768);
        };
        handleResize();
        window.addEventListener("resize", handleResize);
        return () => window.removeEventListener("resize", handleResize);
    }, []);

    if (isMobile && showModal) {
        return <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-40">
            <div className="bg-white fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 
                  text-gray-700 p-6 rounded-lg shadow-lg w-[90vw] max-w-md border border-gray-300 z-50">
                <div className="flex flex-col items-center relative">

                    <p className="text-lg flex flex-col text-center mb-4">
                        <span className="font-semibold">The app works best on larger screens.</span>
                        <span className="text-sm"> We recommend using a desktop or laptop computer for a better user experience.</span>
                    </p>
                    <div className="flex gap-4">
                        <button
                            onClick={() => setShowModal(false)}
                            className="px-4 py-2 text-sm text-gray-700 border border-gray-300 rounded hover:bg-gray-100">
                            Continue anyway
                        </button>
                    </div>
                </div>
            </div>
        </div>
    }

    return <div></div>
}