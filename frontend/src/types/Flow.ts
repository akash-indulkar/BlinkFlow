export interface Flow {
    flowID: number,
    name: string,
    userID: number,
    availableTriggerID: number,
    flowTriggerName: string,
    flowTriggerImage: string,
    flowTriggerMetadata: any,
    flowActions:
    {
        availableActionID: number,
        flowActionName: string,
        flowActionImage: string,
        metadata: any,
        sortingOrder: number
    }[]
}
