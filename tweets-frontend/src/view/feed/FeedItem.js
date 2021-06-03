import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 600px;
  max-width: 100%;
  display: flex;
  padding: 25px;
  margin-bottom: 15px;
  background-color: #fff;
  box-shadow: 0 5px 5px 0 rgba(0, 0, 0, 0.30);
  border-radius: 5px;
`;

const Avatar = styled.img`
  display: block;
  width: 50px;
  height: 50px;
  flex-shrink: 0;
  margin-right: 15px;
  border-radius: 50%;
`;

const Username = styled.span`
  width: 100%;
  display: block;
  font-weight: 600;
  margin-bottom: 15px;
  padding-left: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #d6e3e4;
`;

const Message = styled.p`
  padding-left: 10px;
`;

const FeedItem = ({ tweet }) => {
    const { text, createdBy, profilePictureUrl} = tweet;
    return (
        <>
            <Wrapper>
                <Avatar src={ profilePictureUrl } alt=""/>
                <div>
                    <Username>
                        { createdBy }
                    </Username>
                    <Message>
                        { text }
                    </Message>
                </div>
            </Wrapper>
        </>
    );
};

export default FeedItem;