import React from 'react';
import styled from 'styled-components';

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

const MessageInput = styled.textarea`
  width: 100%;
  min-height: 75px;
  padding: 15px;
  border-radius: 5px;
  border-color: #02333b;
`;

const SubmitButton = styled.button`
  display: block;
  min-width: 130px;
  padding: 10px;
  margin-top: 15px;
  margin-left: auto;
  margin-bottom: -10px;
  border-radius: 30px;
  font-size: 16px;
  background-color: #1c6a77;
  color: #fff;
  cursor: pointer;
  border: none;
  transition: ease-in-out 400ms;


  &:hover {
    background-color: #023b43;
  }
`;

const GroupRight = styled.div`
  flex-grow: 1;
`;


const FeedItem = () => {
    return (
        <>
            <Wrapper>
                <Avatar src="https://source.unsplash.com/random" alt=""/>
                <div>
                    <Username>
                        John Doe
                    </Username>
                    <Message>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis consequat gravida sagittis. Etiam
                        in sapien non augue semper vehicula. Vivamus est mi, facilisis nec leo id, aliquam cursus leo.
                        Nam porttitor elit sit amet purus convallis suscipit.
                    </Message>
                </div>
            </Wrapper>
        </>
    );
}

export const FeedInput = () => {
    return (
        <>
            <Wrapper>
                <Avatar src="https://source.unsplash.com/random" alt=""/>
                <GroupRight>
                    <Username>
                        John Doe
                    </Username>
                    <MessageInput/>
                    <SubmitButton>Submit</SubmitButton>
                </GroupRight>
            </Wrapper>
        </>
    );
}

export default FeedItem;