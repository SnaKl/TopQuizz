var Question = require('./question.model');

exports.getQuestions = async function (query, limit) {

    try {
        var questions = await Question.find(query)
        return questions;
    } catch (e) {
        // Log Errors
        throw Error('Error while getting questions')
    }
}