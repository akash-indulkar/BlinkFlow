export const LogoCarousel = () => {
    const logos = ["https://res.cloudinary.com/dadualj4l/image/upload/v1753358749/slack-new-logo-logo-svgrepo-com_n326gn.svg",
        "https://res.cloudinary.com/dadualj4l/image/upload/v1753357740/mail-bolt_fzzchj.svg",
        "https://res.cloudinary.com/dadualj4l/image/upload/v1753356649/webhook_riiwai.svg",
        "https://res.cloudinary.com/dadualj4l/image/upload/v1753355628/notion-logo-no-background_pvrtrj.png",
        "https://res.cloudinary.com/dadualj4l/image/upload/v1752671898/logo-v3-clickup-symbol-only_sq1dpl.svg",
        "https://res.cloudinary.com/dadualj4l/image/upload/v1752668132/6124991_inmbkr.png",
        "https://res.cloudinary.com/dadualj4l/image/upload/v1752584996/Asana-_avatar_izmu0g.png",
        "https://res.cloudinary.com/dadualj4l/image/upload/v1752497388/Telegram_2019_Logo_ktqy2v.svg"
    ]

    return <div className="relative overflow-hidden py-6 bg-#f9faff">
        <div className="absolute left-0 top-0 h-full w-[90px] md:w-[400px] bg-gradient-to-r from-[#f9faff] to-transparent z-10 pointer-events-none" />
        <div className="absolute right-0 top-0 h-full w-[90px] md:w-[400px] bg-gradient-to-l from-[#f9faff] to-transparent z-10 pointer-events-none" />

        <div className="animate-infinite-scroll flex space-x-8 sm:space-x-12 lg:space-x-16 w-max">
            {[...logos, ...logos].map((logo, index) => (
                <img
                    key={index}
                    src={logo}
                    alt={`Logo ${index}`}
                    className="h-14 sm:h-16 md:h-20 px-4 sm:px-6 shrink-0 w-auto object-contain"
                />
            ))}
        </div>
    </div>
}