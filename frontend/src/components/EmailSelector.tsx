import { useState } from "react";
import { Input } from "./Input";
import { PrimaryButton } from "./buttons/PrimaryButton";

export const EmailSelector = ({setMetadata}: {
    setMetadata: (params: any) => void;
}) => {
    const [email, setEmail] = useState("");
    const [subject, setSubject] = useState("");
    const [body, setBody] = useState("");


    return <div>
        <Input label={"To"} type={"text"} placeholder="To" onChange={(e) => setEmail(e.target.value)}></Input>
        <Input label={"Subject"} type={"text"} placeholder="Subject" onChange={(e) => setSubject(e.target.value)}></Input>
        {/* <textarea label={"Body"} type={"text"} placeholder="Body" onChange={(e) => setBody(e.target.value)}></textarea> */}

        <div className="pt-2">
            <PrimaryButton onClick={() => {
                setMetadata({
                    email,
                    body
                })
            }}>Submit</PrimaryButton>
        </div>
    </div>
}
