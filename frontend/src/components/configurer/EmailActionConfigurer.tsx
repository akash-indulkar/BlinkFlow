import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const EmailActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [emailID, setEmailID] = useState("");
    const [subject, setSubject] = useState("");
    const [body, setBody] = useState("");
    const handleSubmit = () => {
        setMetadata({
            emailID,
            subject,
            body
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"To"} type={"email"} placeholder="To" onChange={(e) => {
            setEmailID(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"Subject"} type={"text"} placeholder="Subject" onChange={(e) => {setSubject(e.target.value) }}></Input>
        <div className="text-sm pt-2 pb-2 flex">
            <label>Body</label><p className="pl-1 text-red-500">*</p>
        </div>
        <textarea required className="mb-2 pl-4 pt-2 w-full min-h-[100px] text-sm text-gray-900 bg-gray-50 rounded border border-gray-300 focus:outline focus:outline-indigo-500" placeholder="Body" onChange={(e) => setBody(e.target.value)}></textarea>
        <BaseButton isInactive={!emailID.trim() || !subject.trim() || !body.trim()}>Submit</BaseButton>
    </form>

}