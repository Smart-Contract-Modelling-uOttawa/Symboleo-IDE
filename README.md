# Symboleo Text Editor
[![DOI](https://zenodo.org/badge/263478039.svg)](https://zenodo.org/badge/latestdoi/263478039)

An IDE for the formal contract specification Symboleo is created using [Xtext](https://www.eclipse.org/Xtext/) DSL generator.
For installating and using the IDE, please take a look at the [installation guide](https://github.com/Smart-Contract-Modelling-uOttawa/Symboleo-IDE/blob/master/INSTALL.md).

# What is Symboleo?
Symboleo is a formal contract specification language developed by [Contract Specification and Modelling (CSM) Lab](https://sites.google.com/uottawa.ca/csmlab/) at Univeristy of Ottawa EECS department. The language aims to enable normative monitoring of smart contracts. Smart contracts are contracts that can monitor their performance (execution). They are a Cyber Physical System (CPS) that utilizes immutable ledgers (such as Distributed Ledger Technology (DLT) Platforms, a.k.a. blockchains).

For more information of Symboleo, please refer the preprint of our accepted paper for RE'20 conference [here](https://drive.google.com/file/d/1WXwXeLrZdaJjhSJcCrt_wBXxDvhFkq2k/view).

# Current State
The current version of the text editor created by Xtext has implemented the syntax of Symboleo. It provides the capability to write Symboleo contract specifications more easily (autofill and syntax highlighting).

# How to Use The IDE?
- Please follow the installation instructions provided in the INSTALL.md file to build the IDE.
- Assuming that the IDE is operational, open the runtime-Eclipse workspace using the steps provided in the installation guide.
- Create a New **General** Project (**File | New | Project… | General | Project**).
- Inside the project, create a new file with the `.symboleo` extension.
- System will prompt you to convert the project as a Xtext project, you should accept that for the Symboleo text editor to work.
- Now you can specify contracts in Symboleo and enjoy its syntax highlighting and autofill capabilities! 
(*Note1: by default in Eclipse, you need to enter `Ctrl` + `Space` to use autofill*).
(*Note2: the auto-fill feature might not suggest all the available options, as some of the grammar rules are implemented in a nested fashion*).

# How to Specify Contracts in Symboleo?
- A contract specification in Symboleo has two main sections: a **Domain** section and a **Contract Body** section. In the the domain section, you will be defining the data-model of the elements that are related to the (business) domain of the contract template you wish to specify. Some basic and ontological concepts (for more information on the ontology please consult [our paper](https://drive.google.com/file/d/1WXwXeLrZdaJjhSJcCrt_wBXxDvhFkq2k/view)) are predefined in the language. Every other concept that is defined in the domain model is an extension of the pre-defined concepts. *Note: Symboleo supports both time points and intervals. Since there is a one-to-one correspondance between events and time-points, and situations and time intervals; they can be used interchangeably.*

|Basic Conepts|Ontological Concepts|
|-------------|--------------------|
|`Enumeration`|`Event`             |
|`String`     |`Asset`             |
|`Number`     |`Role`              |
|`Date`       |`Contract`          |
|`Boolean`    |                    |

- The contrct body has the following parts:

|Contract Body Element|Description|Allowable Inputs|
|---------------------|-----------|----------------|
|`Contract Signature` |contract template ID and contract parameters are defined|`Contract ID` & contract `parameters` and their type|
|`Declarations`|the parameters are bound to the variables which are defined based on the `Domain Model` of the contract|`var`**`isA`**`Type`**`where`**`att_1:=param_1`**`,`**...|
|`Preconditions`|the logical propositions that must be satisfied before a contract can be executed|logical proposition (`Proposition`)|
|`Postconditions`|the logical propositions that must be satisfied after a contract is executed|logical proposition (`Proposition`)|
|`Obligations`|obligations have a name, a trigger(*optional*), two roles, an antecedent and a consequent. the trigger when satisfied, creates an instance of the obligation, while satisfying its antecedent will bring it to an *active state*, i.e. the debtor of the obligation _**must**_ satisfy the consequent.| `oblName`**`:`**`trigger:Proposition`**`-> O(`**`debtor:Role` **`,`**`creditor:Role`**`,`**`antecedent:Proposition`**`,`**`consequent:Proposition`**`)`**|
|`Powers`|powers have a name, a trigger(*optional*), two roles, an antecedent and a consequent. the trigger when satisfied, creates an instance of the power, while satisfying its antecedent will bring it to an *active state*, i.e. the creditor of the power _**can**_ satisfy the consequent which is usually about creating, suspending, resuming or terminating the obligations/contract.| `powName`**`:`**`trigger:Proposition`**`-> P(`**`creditor:Role` **`,`**`debtor:Role`**`,`**`antecedent:Proposition`**`,`**`consequent:Proposition`**`)`**|
|`Constraints`|the logical propositions that must be always satisfied during the execution of the contract|logical proposition (`Proposition`)|

- The sample Sales-of-Goods contract, which is provided in `Symboleo-IDE/samples/MeatSaleContract.symboleo`, illustrates how a simple contract can be specified in Symboleo.
```
Domain meatSaleDomain
  Seller isA Role with returnAddress: String, name: String;
  Buyer isA Role with warehouse: String;
  Currency isAn Enumeration(CAD, USD, EUR);
  MeatQuality isAn Enumeration(PRIME, AAA, AA, A);
  PerishableGood isAn Asset with quantity: Number, quality: MeatQuality;
  Meat isA PerishableGood;
  Delivered isAn Event with item: Meat, deliveryAddress: String, delDueDate: Date;
  Paid isAn Event with amount: Number, currency: Currency, from: Buyer, to: Seller, payDueDate: Date;
  PaidLate isAn Event with amount: Number, currency: Currency, from: Buyer, to: Seller;
  Disclosed isAn Event;
endDomain

Contract MeatSale (buyer : Buyer, seller : Seller, qnt : Number, qlt : MeatQuality, amt : Number, curr : Currency, payDueDate: Date, 
	delAdd : String, effDate : Date, delDueDateDays : Number, interestRate: Number
)

Declarations
  goods: Meat with quantity := qnt, quality := qlt;
  delivered: Delivered with item := goods, deliveryAddress := delAdd, delDueDate := Date.add(effDate, delDueDateDays, days);
  paidLate: PaidLate with amount := (1 + interestRate / 100) * amt, currency := curr, from := buyer, to := seller;
  paid: Paid with amount := amt, currency := curr, from := buyer, to := seller, payDueDate := payDueDate;
  disclosed: Disclosed;

Preconditions
  IsOwner(goods, seller);

Postconditions
  IsOwner(goods, buyer) and not(IsOwner(goods, seller));

Obligations
  delivery: Obligation(seller, buyer, true, WhappensBefore(delivered, delivered.delDueDate));
  payment: O(buyer, seller , true, WhappensBefore(paid, paid.payDueDate));
  latePayment: Happens(Violated(obligations.payment)) -> O(buyer, seller, true, Happens(paidLate));

//Surviving Obligations
//  so1 : Obligation(seller, buyer, true, not WhappensBefore(disclosed, Date.add(Activated(self), 6, months)));
//  so2 : Obligation(buyer, seller, true, not WhappensBefore(disclosed, Date.add(Activated(self), 6, months)));

Powers
  suspendDelivery : Happens(Violated(obligations.payment)) -> Power(seller, buyer, true, Suspended(obligations.delivery));
  resumeDelivery: HappensWithin(paidLate, Suspension(obligations.delivery)) -> P(buyer, seller, true, Resumed(obligations.delivery));
  terminateContract: Happens(Violated(obligations.delivery)) -> P(buyer, seller, true, Terminated(self));

Constraints
  not(IsEqual(buyer, seller));
  CannotBeAssigned(suspendDelivery);
  CannotBeAssigned(resumeDelivery);
  CannotBeAssigned(terminateContract);
  CannotBeAssigned(delivery);
  CannotBeAssigned(payment);
  CannotBeAssigned(latePayment);
  delivered.delDueDate < paid.payDueDate;

endContract
```
# Examples
Example of three contracts with their Symboleo specifications and generated smart contracts are avaialbe in another [repository](https://github.com/Smart-Contract-Modelling-uOttawa/Symboleo2SC-demo).
