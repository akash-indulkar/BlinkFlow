import type { Node, NodeProps } from "@xyflow/react";
import { PrimaryButton } from "../buttons/PrimaryButton";

type AddNodePropsType = Node<{
    onClick: () => void;
}, 'data'>;

export const AddActionNode = ({ data }: NodeProps<AddNodePropsType>) => {
    const {onClick} = data;
    return <div>
        <PrimaryButton onClick={onClick}>
          <div className="text-2xl shadow-xl">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
              <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v6m3-3H9m12 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
            </svg>
          </div>
        </PrimaryButton>
      </div>
}
