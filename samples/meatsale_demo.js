const {
  Obligation, ObligationActiveState, ObligationState, InternalEventType, InternalEvent,
  InternalEventSource, Event, Power, ContractStates, ContractActiveStates, Events,
} = require('symboleo-js-core');
const { MeatSale } = require('./domain/contract/MeatSale.js');
const { Buyer } = require('./domain/roles/Buyer.js');
const { Seller } = require('./domain/roles/Seller.js');
const { Currency } = require('./domain/types/Currency.js');
const { MeatQuality } = require('./domain/types/MeatQuality.js');
const { EventListeners, getEventMap } = require('./events.js');
const { deserialize, serialize } = require('./serializer.js');

const contract = new MeatSale(
  new Buyer('warehouse'),
  new Seller('address', 'seller name'),
  2,
  MeatQuality.AAA,
  10,
  Currency.USD,
  new Date().toISOString(),
  'delivery address',
  new Date().toISOString(),
  15,
  2,
  'test',
);

const d = contract;
Events.init(getEventMap(d), EventListeners);
d.activated();
d.obligations.delivery.trigerredUnconditional();
d.obligations.payment.trigerredUnconditional();
d.survivingObligations.so1.trigerredUnconditional();
d.survivingObligations.so2.trigerredUnconditional();
// console.log(`paid event hasHappened: ${JSON.stringify(d._events)}`);
console.log(`contract isInEffect: ${d.isInEffect()}`);
console.log(`paid event hasHappened: ${d.paid.hasHappened()}`);
console.log(`paidLate event hasHappened: ${d.paidLate.hasHappened()}`);
console.log(`obligations.payment isInEffect: ${d.obligations.payment.isInEffect()}`);
console.log(`obligations.delivery isInEffect: ${d.obligations.delivery.isInEffect()}`);
d.obligations.payment.violated();

console.log(`obligations.payment isViolated: ${d.obligations.payment.isViolated()}`);
console.log(`obligations.payment isInEffect: ${d.obligations.payment.isInEffect()}`);

// console.log('__________________________________________________________________');
const s = serialize(d);
const d2 = deserialize(s);
Events.init(getEventMap(d2), EventListeners);
// console.log(`conract: ${JSON.stringify(d2._events)}`);
console.log(`paid event hasHappened: ${d2.paid.hasHappened()}`);
console.log(`paidLate event hasHappened: ${d2.paidLate.hasHappened()}`);
console.log(`obligations.payment isInEffect: ${d2.obligations.payment.isInEffect()}`);
console.log(`obligations.payment isViolated: ${d2.obligations.payment.isViolated()}`);

console.log('obligations.delivery.suspended');
d2.obligations.delivery.suspended();
d2.powers.suspendDelivery.exerted();

console.log(`obligations.delivery isSuspended: ${d2.obligations.delivery.isSuspended()}`);
console.log(`obligations.latePayment isInEffect: ${d2.obligations.latePayment.isInEffect()}`);

console.log('paidLate.happened');
d2.paidLate.happen();
Events.emitEvent(d2, new InternalEvent(InternalEventSource.contractEvent, InternalEventType.contractEvent.Happened, d2.paidLate));
console.log(`obligations.latePayment isFulfilled: ${d2.obligations.latePayment.isFulfilled()}`);

console.log('obligations.delivery.resumed');
d2.obligations.delivery.resumed();
console.log(`obligations.delivery isInEffect: ${d.obligations.delivery.isInEffect()}`);
console.log(`obligations.delivery isFulfilled: ${d2.obligations.delivery.isFulfilled()}`);

console.log('delivered.happened');
d2.delivered.happen();
Events.emitEvent(d2, new InternalEvent(InternalEventSource.contractEvent, InternalEventType.contractEvent.Happened, d2.delivered));

console.log(`obligations.delivery isFulfilled: ${d2.obligations.delivery.isFulfilled()}`);

console.log('__________________________________________________________________');
const d3 = deserialize(serialize(d));
console.log(`obligations.delivery isFulfilled: ${d2.obligations.delivery.isFulfilled()}`);

// eslint-disable-next-line no-useless-escape
const sample = '{\"buyer\": {\"warehouse\": \"warehouse add\"},\"seller\": {\"returnAddress\": \"add\", \"name\": \"seller name\"},\"qnt\": 2,\"qlt\": 3,\"amt\": 3,\"curr\": 1,\"payDueDate\": \"2022-03-28T17:49:41.422Z\",\"delAdd\": \"delAdd\",\"effDate\": \"2022-03-28T17:49:41.422Z\",\"delDueDateDays\": 3,\"interestRate\": 2,\"test\": \"test\",}';
