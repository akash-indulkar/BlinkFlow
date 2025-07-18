import { useCallback, useEffect, useState } from 'react';
import {
  Background,
  ReactFlow,
  useNodesState,
  useEdgesState,
  type Node,
  type Edge,
  NodeTypes,
  MarkerType,
} from '@xyflow/react';

import '@xyflow/react/dist/style.css';
import { CustomNode } from '../components/ReactFlow/CustomNode';
import axios from 'axios';
import { PrimaryButton } from '../components/buttons/PrimaryButton';
import { useNavigate, useParams } from 'react-router-dom';
import { AddActionNode } from '../components/ReactFlow/AddActionNode';
import { Modal } from '../components/Modal';
import toast from 'react-hot-toast';
import { Loader } from '../components/Loader';


const backendURL = import.meta.env.VITE_BACKEND_URL;

function useAvailableActionsAndTriggers() {
  const [availableActions, setAvailableActions] = useState([]);
  const [availableTriggers, setAvailableTriggers] = useState([]);

  useEffect(() => {
    axios.get(`${backendURL}/triggers/availabletriggers`)
      .then(res => setAvailableTriggers(res.data))

    axios.get(`${backendURL}/actions/availableactions`)
      .then(res => setAvailableActions(res.data))
  }, [])

  console.log(availableTriggers)
  console.log(availableActions)
  return {
    availableActions,
    availableTriggers
  }
}

const nodeTypes: NodeTypes = {
  customNode: CustomNode,
  addActionNode: AddActionNode,
}

const nodeOrigin: [number, number] = [0.5, 0];

export const EditFlow = () => {
  const router = useNavigate();
  const { flowID } = useParams();
  const { availableActions, availableTriggers } = useAvailableActionsAndTriggers();
  const [existingFlowName, setExistingFlowName] = useState<string>("")
  const [existingTrigger, setExistingTrigger] = useState<{ availableTriggerID: number, name: string, image: string, metadata: any }>();
  const [existingActions, setExistingActions] = useState<{
    index: number;
    availableActionId: number;
    availableActionName: string;
    availableActionImage: string;
    metadata: any;
  }[]>([]);
  const [selectedTrigger, setSelectedTrigger] = useState<{ availableTriggerID: number, name: string, image: string, metadata: any }>();
  const [selectedActions, setSelectedActions] = useState<{
    index: number;
    availableActionId: number;
    availableActionName: string;
    availableActionImage: string;
    metadata: any;
  }[]>([]);
  const [nodes, setNodes, onNodesChange] = useNodesState<Node>([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState<Edge>([]);
  const [selectedModalIndex, setSelectedModalIndex] = useState<null | number>(null);
  const [flowName, setFlowName] = useState<string>(existingFlowName);
  const [isEditing, setIsEditing] = useState(false);
  const [userID, setUserID] = useState();
  const [isLoading, setIsLoading] = useState<boolean>(true)

  useEffect(() => {
    axios.get(`${backendURL}/flow/${flowID}`, {
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-type": "application/json"
      }
    })
      .then(response => {
        setExistingFlowName(response.data.name)
        setUserID(response.data.userID)
        setExistingTrigger({
          availableTriggerID: response.data.availableTriggerID,
          name: response.data.flowTriggerName,
          image: response.data.flowTriggerImage,
          metadata: response.data.flowTriggerMetadata
        })
        const formattedActions = response.data.flowActions.map((action: {
          availableActionID: number,
          flowActionName: string,
          flowActionImage: string,
          metadata: any,
          sortingOrder: number
        }) => ({
          index: action.sortingOrder,
          availableActionId: action.availableActionID,
          availableActionName: action.flowActionName,
          availableActionImage: action.flowActionImage,
          metadata: action.metadata
        }));
        setExistingActions(formattedActions)
        console.log(response.data)
      })
  }, [])

  useEffect(() => {
    if (existingTrigger && existingActions) {
      const initialActions = existingActions.map((action) => ({
        index: action.index,
        availableActionId: action.availableActionId,
        availableActionName: action.availableActionName,
        availableActionImage: action.availableActionImage,
        metadata: action.metadata,
      }));
      setSelectedActions(initialActions);
      setSelectedTrigger(existingTrigger)
      setFlowName(existingFlowName)
    }
  }, [existingTrigger, existingActions]);

  const addAction = useCallback(() => {
    setSelectedActions(prev => [
      ...prev,
      {
        index: prev.length + 2,
        availableActionId: 0,
        availableActionName: '',
        availableActionImage: '',
        metadata: {}
      }
    ]);
  }, []);

  useEffect(() => {
    const newNodes: Node[] = [];

    newNodes.push({
      id: '0',
      type: 'customNode',
      position: { x: 775, y: 150 },
      data: {
        name: selectedTrigger?.name || "Select a Trigger",
        image: selectedTrigger?.image || "",
        index: 1,
        type: "Trigger",
        onClick: () => setSelectedModalIndex(1),
        onDelete: () => { }
      }
    });
    selectedActions.forEach((action, i) => {
      newNodes.push({
        id: `${action.index}`,
        type: 'customNode',
        position: { x: 775, y: 150 + (i + 1) * 120 },
        data: {
          name: action.availableActionName || "Select an Action",
          image: action.availableActionImage || "",
          index: action.index,
          type: "Action",
          onClick: () => setSelectedModalIndex(action.index),
          onDelete: () => {
            setSelectedActions(prev =>
              prev
                .filter(a => a.index !== action.index)
                .map((a, newIndex) => ({
                  ...a,
                  index: newIndex + 2,
                }))
            );
          }
        }
      });
    });

    newNodes.push({
      id: 'add-button',
      type: 'addActionNode',
      position: {
        x: 775,
        y: 240 + selectedActions.length * 120,
      },
      data: {
        onClick: addAction
      }
    });

    const newEdges: Edge[] = [];
    if (selectedActions.length > 0) {
      newEdges.push({
        id: `e-0-${selectedActions[0].index}`,
        source: '0',
        target: `${selectedActions[0].index}`,
        type: 'straight',
        style: { stroke: '  #994d00', strokeWidth: 2, strokeDasharray: '1.5 1.6' },
        markerEnd: {
          type: MarkerType.ArrowClosed,
          color: '#994d00'
        }
      });

      for (let i = 1; i < selectedActions.length; i++) {
        newEdges.push({
          id: `e-${selectedActions[i - 1].index}-${selectedActions[i].index}`,
          source: `${selectedActions[i - 1].index}`,
          target: `${selectedActions[i].index}`,
          type: 'straight',
          style: { stroke: '  #994d00', strokeWidth: 2, strokeDasharray: '1.5 1.6' },
          markerEnd: {
            type: MarkerType.ArrowClosed,
            color: '#994d00'
          }
        });
      }
    }
    setNodes(newNodes);
    setEdges(newEdges);
    setIsLoading(false)
  }, [selectedActions, selectedTrigger]);

  return (
    <div style={{ height: '100vh', position: 'relative' }}>
      {isLoading && <Loader />}
      <div onClick={() => setIsEditing(true)} onBlur={() => setIsEditing(false)} className="absolute top-20 left-1/2 transform -translate-x-1/2 z-50 bg-white/80 backdrop-blur-md shadow-xl px-4 py-2 rounded-full flex items-center gap-2 border border-gray-200">
        {isEditing ? (
          <input
            value={flowName}
            onChange={(e) => setFlowName(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") setIsEditing(false);
            }}
            autoFocus
            className="bg-transparent outline-none font-sans text-base antialiased"
          />
        ) : (
          <>
            <h2 className="truncate overflow-hidden text-ellipsis max-w-[140px] font-sans text-base antialiased">{flowName}</h2>
            <button
              className="hover:text-blue-500 transition-colors"
            >
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-5">
                <path strokeLinecap="round" strokeLinejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L6.832 19.82a4.5 4.5 0 0 1-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 0 1 1.13-1.897L16.863 4.487Zm0 0L19.5 7.125" />
              </svg>

            </button>
          </>
        )}
      </div>
      <div className="flex justify-end p-2 absolute right-0 top-20 z-50">
        <PrimaryButton onClick={() => {
          router("/dashboard")
        }}>Go to Dashboard</PrimaryButton>
      </div>
      <div className="flex justify-end p-4 absolute right-0 top-[120px] z-50">
        <PrimaryButton onClick={async () => {
          if (!selectedTrigger?.availableTriggerID) {
            return;
          }
          console.log({
            "userID": userID,
            "name": flowName,
            "availableTriggerID": selectedTrigger.availableTriggerID,
            "triggerMetadata": selectedTrigger.metadata,
            "flowActions": selectedActions.map(action => ({
              availableActionID: action.availableActionId,
              metadata: action.metadata,
              sortingOrder: action.index
            }))
          }, {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token")
            }
          })
          await axios.put(`${backendURL}/flow/update/${flowID}`, {
            "userID": userID,
            "name": flowName,
            "availableTriggerID": selectedTrigger.availableTriggerID,
            "triggerMetadata": selectedTrigger.metadata,
            "flowActions": selectedActions.map(action => ({
              availableActionID: action.availableActionId,
              metadata: action.metadata,
              sortingOrder: action.index
            }))
          }, {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token")
            }
          })
          toast.success("Flow updated successfully!")
          router("/dashboard");
        }}> Save Changes </PrimaryButton>
      </div>
      {selectedModalIndex && <Modal availableItems={selectedModalIndex === 1 ? availableTriggers : availableActions} onSelect={(props: null | { name: string; id: number; image: string, metadata: any; }) => {
        if (props === null) {
          setSelectedModalIndex(null);
          return;
        }
        if (selectedModalIndex === 1) {
          setSelectedTrigger({
            availableTriggerID: props.id,
            name: props.name,
            image: props.image,
            metadata: props.metadata
          })
        } else {
          setSelectedActions(actions => {
            let newActions = [...actions];
            newActions[selectedModalIndex - 2] = {
              index: selectedModalIndex,
              availableActionId: props.id,
              availableActionName: props.name,
              metadata: props.metadata,
              availableActionImage: props.image
            }
            return newActions
          })
        }
        setSelectedModalIndex(null);
      }} index={selectedModalIndex} />}

      <ReactFlow
        nodes={nodes}
        edges={edges}
        onNodesChange={onNodesChange}
        onEdgesChange={onEdgesChange}
        nodeTypes={nodeTypes}
        zoomOnScroll={false}
        nodeOrigin={nodeOrigin}
        defaultViewport={{ x: 0, y: 0, zoom: 1 }}
        nodesDraggable={false}
        zoomOnDoubleClick={false}
      >
        <Background gap={5} size={0.5} />
      </ReactFlow>
    </div>
  );
}

