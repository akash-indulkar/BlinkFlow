export const FlowCell = ({
    name,
    index,
    onClick
}: {
    name?: string; 
    index: number;
    onClick: () => void;
}) => {
    return <div onClick={onClick} className="border-dotted border-orange-800 border-2 rounded-lg bg-orange-100 py-8 px-8 flex w-[300px] justify-center cursor-pointer">
        <div className="flex text-xl">
            <div className="font-bold">
                {index}. 
            </div>
            <div>
                {name}
            </div>
        </div>
    </div>
}