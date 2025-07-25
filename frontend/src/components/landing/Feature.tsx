
export const Feature = ({title, subtitle}: {
    title: string,
    subtitle: string
}) => {
    return <div className=" flex px-4 sm:px-8 gap-x-1">
        <Check />
        <div className="flex flex-col justify-center">
            <div className="flex flex-wrap text-sm sm:text-base">
                <div className="font-opensans">
                    <strong>{title}{" "}</strong> 
                    {subtitle}
                </div>
            </div>
        </div>
    </div>
}

function Check () {
    return <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="size-5">
    <path strokeLinecap="round" strokeLinejoin="round" d="m4.5 12.75 6 6 9-13.5" />
  </svg>
}