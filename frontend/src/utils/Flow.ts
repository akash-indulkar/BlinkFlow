export interface Flow {
    "id": string,
    "triggerId": string,
    "userId": number,
    "actions": {
        "id": string,
        "flowId": string,
        "actionId": string,
        "sortingOrder": number,
        "type": {
            "id": string,
            "name": string
            "image": string
        }
    }[],
    "trigger": {
        "id": string,
        "flowId": string,
        "triggerId": string,
        "type": {
            "id": string,
            "name": string,
            "image": string
        }
    }
}
