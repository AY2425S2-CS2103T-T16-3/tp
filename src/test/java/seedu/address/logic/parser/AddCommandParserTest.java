package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOBTITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.JOBTITLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.JOBTITLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LABEL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LABEL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBTITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBTITLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LABEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LABEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_LEETCODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JobTitle;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + JOBTITLE_DESC_BOB + LABEL_DESC_BOB
                        + REMARK_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + JOBTITLE_DESC_BOB
                         + LABEL_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + TAG_DESC_FRIEND;


        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple labels
        assertParseFailure(parser, LABEL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LABEL));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + JOBTITLE_DESC_AMY + LABEL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE,
                        PREFIX_LABEL));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOBTITLE_DESC_AMY + LABEL_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPerson));

        // no remarks
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJobTitle(VALID_JOBTITLE_AMY).withLabel(VALID_LABEL_AMY)
                .withTags(VALID_TAG_FRIEND)
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOBTITLE_DESC_AMY + LABEL_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // no schedule
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJobTitle(VALID_JOBTITLE_AMY).withLabel(VALID_LABEL_AMY)
                .withRemark(VALID_REMARK_LEETCODE).withTags(VALID_TAG_FRIEND)
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOBTITLE_DESC_AMY + LABEL_DESC_AMY + REMARK_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // zero tags and no schedule
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJobTitle(VALID_JOBTITLE_AMY).withLabel(VALID_LABEL_AMY)
                .withRemark(VALID_REMARK_LEETCODE)
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOBTITLE_DESC_AMY + LABEL_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPerson));

        // zero tags and no remarks
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJobTitle(VALID_JOBTITLE_AMY).withLabel(VALID_LABEL_AMY)
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOBTITLE_DESC_AMY + LABEL_DESC_AMY,
                new AddCommand(expectedPerson));

        // no remarks and no schedule
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJobTitle(VALID_JOBTITLE_AMY).withLabel(VALID_LABEL_AMY)
                .withTags(VALID_TAG_FRIEND)
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOBTITLE_DESC_AMY + LABEL_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // zero tags, no remarks and no schedule
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJobTitle(VALID_JOBTITLE_AMY).withLabel(VALID_LABEL_AMY)
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOBTITLE_DESC_AMY + LABEL_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_NAME));

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_PHONE));

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_EMAIL));

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_ADDRESS));

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_JOBTITLE));

        // missing jobTitle prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + VALID_JOBTITLE_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_LABEL));

        // missing label prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + JOBTITLE_DESC_BOB + VALID_LABEL_BOB, expectedMessage);

        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_JOBTITLE, PREFIX_LABEL));

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                        + VALID_JOBTITLE_BOB + VALID_ADDRESS_BOB + VALID_LABEL_BOB + REMARK_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS);

        // invalid jobTitle
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INVALID_JOBTITLE_DESC + LABEL_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                JobTitle.MESSAGE_NEW_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB
                        + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + JOBTITLE_DESC_BOB + LABEL_DESC_BOB
                        + REMARK_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + JOBTITLE_DESC_BOB + LABEL_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE));
    }
}
