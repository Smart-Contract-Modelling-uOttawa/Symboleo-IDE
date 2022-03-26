# Deploy to Hyperledger Fabric 
To deploy the generated smart contract to Hyperledger Fabric blockchain, we need to install docker and the Fabric test network. You need to install [WSL](https://docs.microsoft.com/en-us/windows/wsl/install) on Windows to install docker.

## Installing Hyperledger Fabric
1- To install WSL open a Windows terminal and run the following command. If you have WSL installed skip this step.
```bash
wsl --install
```
1- After successful installation of WSL, reboot your PC. Then open another Windows terminal and run these commands.
```bash
wsl --set-default-version 2
ubuntu          ## this will open the Ubuntu terminal (if this is your first time running Ubuntu on WSL you need to setup a password for your user).
```
3- In the Ubuntu terminal run these commands.
```bash
sudo apt update
sudo apt upgrade
sudo apt install git
```
4- Follow the instructions in the official docker [website](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository) to install docker inside your Ubuntu (follow the "Install using the repository" method). 

5- After installing docker you need to manually start the docker service. In the Ubuntu terminal run this command.
```bash
sudo service docker start
```
6- To verify that docker works correctly run:
```bash
sudo docker run hello-world
```
7- Now we need to install `docker-compose`.
```bash
sudo apt-get -y install docker-compose
docker-compose --version
```
8- The next step is to install Hyperledger Fabric test network.
```bash
cd ~
mkdir symboleo
cd symboleo
curl -sSL https://bit.ly/2ysbOFE | bash -s
```
## Deploying and testing a contract
You have to run the following steps everytime you want to run a contract after reboot or changing its code.  
1- First, we should copy the generated contract source code into the Ubuntu filesystem.
```bash
cd ~/symboleo/fabric-samples
mkdir meatsale
cp /mnt/c/path/to/MeatSale/. meatsale/. -R  ## you can access your C Windows driver from the /mnt/c/ path
```
2- The second step is to start the Hyperledger Fabric containers.
```bash
## if docker is not running, first, start docker service
## sudo service docker start
cd test-network
./network.sh up createChannel
## now deploty the contract
./network.sh deployCC -ccn meatsale -ccp ../meatsale -ccl javascript
```
3- After deploying the contract, we can call any transaction using the CLI, but, before that we need to populate a few ENV variables. The Fabric test network consists of two Organisations. Run the following lines to set the values for `Org1`. 
```bash
export PATH=${PWD}/../bin:$PATH
export FABRIC_CFG_PATH=$PWD/../config/

export CORE_PEER_TLS_ENABLED=true
export CORE_PEER_LOCALMSPID="Org1MSP"
export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
export CORE_PEER_ADDRESS=localhost:7051
```
4- The first transaction to call must always be the `init` transaction to activate the Symboleo contract. As you can see in the command, the name of transaction (name of the functions in the `index.js` file) is passed in the `function` argument, and all of the paramaters are passed as string in the `Args` argument.
```bash
peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem" -C mychannel -n meatsale --peerAddresses localhost:7051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt" --peerAddresses localhost:9051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt" -c '{"function":"init","Args":["{\"buyer\":  {\"warehouse\": \"warehouse add\"},\"seller\":  {\"returnAddress\": \"add\", \"name\": \"seller name\"},\"qnt\": 2,\"qlt\": 3,\"amt\": 3,\"curr\": 1,\"payDueDate\": \"2022-10-28T17:49:41.422Z\",\"delAdd\": \"delAdd\",\"effDate\": \"2022-10-28T17:49:41.422Z\",\"delDueDateDays\": 3,\"interestRate\": 2}"]}'
```
After successful invocation you will see a message like this:
```
 Chaincode invoke successful. result: status:200 payload:"{\"successful\":true,\"contractId\":\"MeatSale_202222420\"}"
```
Please take a copy of the `contractId`  value, we need it for future invocations.  
5- You can call any other transaction too. For example, to call the `trigger_paid` and send the paid event, run this command. Replace the `contractId` with your own value:
```bash
peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem" -C mychannel -n meatsale --peerAddresses localhost:7051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt" --peerAddresses localhost:9051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt" -c '{"function":"trigger_paid","Args":["{\"contractId\": \"MeatSale_202222420\", \"event\": {}}"]}'
```
6- To query the state of the contract, replace the second argument below with your own `contractId` value and run:
```bash
peer chaincode query -C mychannel -n meatsale -c '{"Args":["getState", "MeatSale_202222420"]}'
```
7- To call the `trigger_delivered` replace the `contractId` with your own value and run:
```bash
peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem" -C mychannel -n meatsale --peerAddresses localhost:7051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt" --peerAddresses localhost:9051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt" -c '{"function":"trigger_delivered","Args":["{\"contractId\": \"MeatSale_202222420\", \"event\": {}}"]}'
```
8- Finally, to shutdown the netowrk run:
```bash
./network.sh down
```
  
To test the contract again or update it, you must shutdown the network and deploy it again.