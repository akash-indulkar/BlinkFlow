import { Handle, Position, type Node, type NodeProps } from '@xyflow/react';

type CustomNodePropsType = Node<{
    name: string;
    index: number;
    type: string;
    image: string;
    onClick: () => void;
    onDelete: () => void;
}, 'data'>;
export const CustomNode = ({ data }: NodeProps<CustomNodePropsType>) => {
    const { name, index, type, image, onClick, onDelete } = data;
    if (type === "Trigger") {
        return <div onClick={onClick} className="hover:border-indigo-700 hover:border-solid border-dotted border-orange-800 border-2 rounded-lg shadow-xl bg-orange-100 py-6 px-4 flex w-[300px] justify-center cursor-pointer transition-colors duration-300">
            <div className="flex text-xl">
                <div className="text-lg">
                    {index}.
                </div>
                {(name === "Select a Trigger") ? <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" height="26" width="26"  color="GrayWarm10" name="miscBoltOutlined"><path fill="#2D2E2E" d="M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20Zm0 18a8 8 0 1 1 0-16 8 8 0 0 1 0 16Zm1-14-5.87 7H11v5l5.87-7H13V6Z"></path></svg> : <div><img src={image} width={30} className="rounded-full" /> <div className="flex flex-col justify-center" /></div>}
                <div className='text-lg'>
                    {name}
                </div>
            </div>
            <Handle className='hide-handle' type="source" position={Position.Bottom} />
        </div>
    } else {
        return <div>
            <Handle className='hide-handle' type="target" position={Position.Top} />
            <div className="hover:border-indigo-700 hover:border-solid border-dotted border-orange-800 border-2 rounded-lg shadow-xl bg-orange-100  w-[300px] flex  justify-center cursor-pointer">
                <div className="flex text-xl justify-center">
                    <div onClick={onClick} className="w-[260px] flex justify-center py-6 px-6">
                        <div className="text-lg">
                            {index}.
                        </div>
                        {(name === "Select an Action") ? <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" height="26" width="26" color="GrayWarm10" name="miscBoltOutlined"><path fill="#2D2E2E" d="M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20Zm0 18a8 8 0 1 1 0-16 8 8 0 0 1 0 16Zm1-14-5.87 7H11v5l5.87-7H13V6Z"></path></svg> : <div><img src={image} width={30} className="rounded-full" /> <div className="flex flex-col justify-center" /></div>}
                        <div className='text-lg overflow-hidden whitespace-nowrap text-ellipsis'>
                            {name}
                        </div>
                    </div>
                    <svg onClick={onDelete} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="text-gray-400 text-sm border-black w-5 h-5 absolute top-2 right-2 bg-transparent hover:border-black hover:text-red-500 rounded-lg ">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M11.412 15.655 9.75 21.75l3.745-4.012M9.257 13.5H3.75l2.659-2.849m2.048-2.194L14.25 2.25 12 10.5h8.25l-4.707 5.043M8.457 8.457 3 3m5.457 5.457 7.086 7.086m0 0L21 21" />
                    </svg>
                </div>
            </div>
            <Handle className='hide-handle' type="source" position={Position.Bottom} />
        </div>
    }

}