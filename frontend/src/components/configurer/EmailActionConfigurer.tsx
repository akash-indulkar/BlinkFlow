import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const EmailActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [emailID, setEmailID] = useState("");
    const [subject, setSubject] = useState("");
    const handleSubmit = () => {
        setMetadata({
            emailID,
            subject
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"To"} type={"email"} placeholder="To" onChange={(e) => {
            setEmailID(e.target.value)
            e.target.setCustomValidity("")
        }}/>
        <div className="pb-4">
            <Input label={"Subject"} type={"text"} placeholder="Subject" onChange={(e) => { setSubject(e.target.value) }} />
        </div>
        <BaseButton isInactive={!emailID.trim() || !subject.trim()}>Submit</BaseButton>
    </form>

}