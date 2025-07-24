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

    return <div className="relative overflow-hidden py-8 bg-#f9faff">
        <div className="absolute left-0 top-0 h-full w-[300px] bg-gradient-to-r from-white to-transparent z-10 pointer-events-none" />
        <div className="absolute right-0 top-0 h-full w-[300px] bg-gradient-to-l from-white to-transparent z-10 pointer-events-none" />

        <div className="animate-scroll flex space-x-16 w-max">
            {[...logos, ...logos].map((logo, index) => (
                <img
                    key={index}
                    src={logo}
                    alt={`Logo ${index}`}
                    className="h-20 px-6 shrink-0 w-auto object-contain"
                />
            ))}
        </div>
    </div>
}