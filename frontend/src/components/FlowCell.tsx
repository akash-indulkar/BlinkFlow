export const FlowCell = ({
    name,
    index,
    type,
    image,
    onClick,
    onDelete
}: {
    name?: string;
    index: number;
    type: string;
    image: string;
    onClick: () => void;
    onDelete: () => void;
}) => {
    if (type === "Trigger") {
        return <div onClick={onClick} className="border-dotted border-orange-800 border-2 rounded-lg bg-orange-100 py-8 px-8 flex w-[300px] justify-center cursor-pointer">
            <div className="flex text-xl">
                <div className="font-bold">
                    {index}.
                </div>
                {(name === "Select a Trigger") ? <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6 w-6 h-6">
                    <path strokeLinecap="round" strokeLinejoin="round" d="m3.75 13.5 10.5-11.25L12 10.5h8.25L9.75 21.75 12 13.5H3.75Z" />
                </svg> : <div><img src={image} width={30} className="rounded-full" /> <div className="flex flex-col justify-center" /></div>}
                <div>
                    {name}
                </div>
            </div>
        </div>
    } else {
        return <div>
            <div className="border-dotted border-orange-800 border-2 rounded-lg bg-orange-100  flex  justify-center cursor-pointer">
                <div className="flex text-xl justify-between">
                    <div onClick={onClick} className="w-[260px] flex py-8 px-8">
                        <div className="font-bold">
                            {index}.
                        </div>
                        {(name === "Select an Action") ? <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6 w-6 h-6">
                            <path strokeLinecap="round" strokeLinejoin="round" d="m3.75 13.5 10.5-11.25L12 10.5h8.25L9.75 21.75 12 13.5H3.75Z" />
                        </svg> : <div><img src={image} width={30} className="rounded-full" /> <div className="flex flex-col justify-center" /></div>}
                        <div>
                            {name}
                        </div>
                    </div>
                        <svg onClick={onDelete} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="hover:stroke-white rounded-r-lg w-8 h-full hover:bg-red-400 pt-7 pb-7">
                            <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0" />
                        </svg>
                </div>

            </div>

        </div>
    }

}