import React, { useState } from "react";
import config from "../config.json";
import TextField from '@material-ui/core/TextField';
import Button from "@material-ui/core/Button"

function GenerateUrl() {
    const [inputUrl, setInputUrl] = useState("");
    const [fullUrl, setFullUrl] = useState("");
    const [shortenedUrl, setShortenedUrl] = useState("");
    const [invalidUrlFlag, setInvalidUrlFlag] = useState(false);
    const handleInputUrlChange = (event) => {
        console.log("url change")
        setInputUrl(event.target.value)
    }
    const handleSubmit = () => {
        console.log("sending submit request")
        fetch(config.SERVER_HOST + "/", {
          method: 'POST',
          headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
          },
            body: JSON.stringify(inputUrl)
        })
            .then(response => response.json())
            .then(json => {
                if (json.status == 400 && json.message == "Invalid URL") {
                    setInvalidUrlFlag(true)
                } else {
                    setFullUrl(json.fullUrl)
                    setShortenedUrl(json.shortenedUrl)
                }
            })
            .catch(err=>{
                console.log("error:")
            })
    };
    return (
        <div>
            <form>
                <TextField id="outlined-basic" label="Enter Url" variant="outlined" value={inputUrl} onChange={handleInputUrlChange} />
            </form>
            <Button onClick={handleSubmit} type="submit"> Submit </Button>
            <h4>
                {invalidUrlFlag ? <p>Invalid URL!</p>:<p></p>}
            </h4>
            <h3>Full Url: {fullUrl}</h3>
            <h3>Shortened Url: {shortenedUrl} </h3>

        </div>
    )
}

export default GenerateUrl;