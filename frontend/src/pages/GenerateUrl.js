import React, { useState } from "react";
import config from "../config.json";
import TextField from '@material-ui/core/TextField';
import Button from "@material-ui/core/Button"
import FormControl from '@material-ui/core/FormControl'
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import {useParams} from "react-router-dom";

export default function GenerateUrl() {
    let {isRedirect} = useParams()
    const [redirected, setRedirected] = useState(isRedirect)
    const [inputUrl, setInputUrl] = useState("");
    const [fullUrl, setFullUrl] = useState("");
    const [shortenedUrl, setShortenedUrl] = useState("");
    const [invalidUrlFlag, setInvalidUrlFlag] = useState(false);

    const handleInputUrlChange = (event) => {
        setInputUrl(event.target.value)
    }

    const handleSubmit = async () => {
        console.log("sending submit request")
        const response = await fetch(config.SERVER_HOST + "/", {
          method: 'POST',
          headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
          },
            body: JSON.stringify(inputUrl)
        })
        console.log(response)
        const json = await response.json()
        console.log(json)
        if (json.status === 400 && json.message === "Invalid URL") {
            setInvalidUrlFlag(true)
        } else {
            setFullUrl(json.fullUrl)
            setShortenedUrl(json.shortenedUrl)
            setInvalidUrlFlag(false)
        }
        setRedirected(false)
    };

    return (
        <div>
            {
                isRedirect && <Typography style ={{color: "red"}}>Shortened URL does not exists! Generate one here:</Typography>
            }
            <Card elevation={4} style={{ margin: 18, maxWidth: '80%' }}>
                <CardContent>
                    <Typography variant={"h6"} gutterBottom>
                        Enter the URL you wish to shorten!
                    </Typography>
                    <FormControl fullWidth>
                        <TextField id="outlined-basic" label="Enter Url" variant="outlined" value={inputUrl} onChange={handleInputUrlChange} />
                    </FormControl>
                    <Button onClick={handleSubmit} type="submit" variant="contained" color="primary"> Submit </Button>
                </CardContent>
                <React.Fragment>
                    {
                        invalidUrlFlag ? <Typography style ={{color: "red"}}>Invalid URL!</Typography> :
                            fullUrl && shortenedUrl ?
                                <Container style={{outline: "black", backgroundColor: "#7FFFD4"}}>
                                    <Typography variant={"subtitle1"}>
                                        <span style={{fontWeight: "bold"}}> Full Url: </span>
                                        {fullUrl}
                                        <br/>
                                        <span style={{fontWeight: "bold"}}> Shortened Url: </span>
                                        {shortenedUrl}
                                    </Typography>
                                </Container> : null
                    }
                </React.Fragment>
            </Card>
        </div>
    )
}
